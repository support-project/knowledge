<template>
  <div>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      
      <page-title
        :title = "'Route.' + $route.name"
        :description = "$route.name + '.description'"
        :breadcrumb = "breadcrumb" />

      <div id="secondNavbar" class="left-margin-content">
        <nav class="secondMenu" >
          <a :title="$t('ArticleEdit.BtnAttributes')"
            v-bind:class="{'toggle-on': pagestate.showRightSideBar}"
            v-on:click="toggleRightSideBar()">
            <i class="fa fa-list fa-lg" aria-hidden="true"></i>
          </a>
          <button :title="$t('ArticleEdit.BtnRelease')" class="label-primary" v-on:click="releaseArticle()">
            <i class="fa fa-rocket fa-lg" aria-hidden="true"></i>
          </button>
          <a :title="$t('ArticleEdit.BtnDraft')" v-on:click="saveDraftArticle()">
            <i class="fa fa-save fa-lg" aria-hidden="true"></i>
          </a>
          <router-link tag="a" :to="backhref"
            :title="$t('ArticleEdit.BtnCancel')">
            <i class="fa fa-undo fa-lg" aria-hidden="true"></i>
          </router-link>
          <a :title="$t('ArticleEdit.BtnRemove')" class="label-danger">
            <i class="fa fa-remove fa-lg" aria-hidden="true"></i>
          </a>
        </nav>
      </div>

      <!-- Main content -->
      <div class="content main-content">
        <div class="article-meta" v-if="article.draftId" >
          <span class="exist-draft">{{ $t("ArticleEdit.ExistDraft") }}</span>
          <button class="btn btn-default btn-xs" v-on:click="deleteDraft()">
            <i class="fa fa-eraser"></i>&nbsp;{{ $t("ArticleEdit.DeleteDraft") }}
          </button>
        </div>

        <alerts></alerts>
        <form role="form" id="knowledgeForm">
          <div class="form-group">
              <label for="input_title">
                Title
              </label>
              <div class="input-group">
                <span class="input-group-addon">
                  <span class="fa fa-bookmark"></span>
                </span>
                <input type="text" class="form-control" name="title" placeholder="Title"
                  v-model="article.title" />
              </div>
          </div>

          <article-edit-items />

          <markdown-editor :article="article" :rows="20" />

        </form>
      </div>
    </div>
    <!-- /.content-wrapper -->

    <article-edit-sidebar :article="article" />

    <target-select-dialog />

  </div>
</template>

<script>
import tippy from 'tippy.js'
import { mapState } from 'vuex'
import logger from 'logger'

import PageTitle from '../Parts/PageTitle'
import ArticleEditSidebar from './ArticleEditSidebar'
import ArticleEditItems from './ArticleEditItems'

import Alerts from '../Parts/Alerts'
import MarkdownEditor from '../Parts/MarkdownEditor'
import TargetSelectDialog from '../Parts/TargetSelectDialog'
import secondNavbar from '../../../lib/displayParts/secondNavbar'
import rightSidebar from './../../../lib/displayParts/rightSidebar'

const LABEL = 'ArticleEdit.vue'

export default {
  name: 'ArticleEdit',
  data () {
    var breadcrumb = [
      {to: '/articles/new', name: 'Route.ArticleCreate'}
    ]
    var backhref = '/articles'
    if (this.$route.params.id) {
      breadcrumb = [
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'},
        {to: '/articles/' + this.$route.params.id + '/edit', name: 'Route.ArticleEdit'}
      ]
      backhref = '/articles/' + this.$route.params.id
    }
    return {
      breadcrumb: breadcrumb,
      backhref: backhref
    }
  },
  components: { PageTitle, ArticleEditSidebar, MarkdownEditor, ArticleEditItems, Alerts, TargetSelectDialog },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      article: state => state.article.article,
      types: state => state.types.types
    })
  },
  methods: {
    getArticle () {
      logger.debug(LABEL, 'getArticle')
      // 右側のサイドバーの状態を復元
      rightSidebar(this.$store.state.pagestate.showRightSideBar)
      // データの取得
      if (this.$route.params.draftId) {
        // 下書きから（まだ記事を投稿前)
        logger.info(LABEL, 'edit draft. ' + this.$route.params.draftId)
        this.$store.dispatch('article/getDraftForEdit', this.$route.params.draftId)
      } else if (this.$route.params.id) {
        logger.info(LABEL, 'edit article. ' + this.$route.params.id)
        this.$store.dispatch('article/getArticleForEdit', this.$route.params.id).then(() => {
          return this.$store.dispatch('types/loadTypes')
        })
      } else {
        logger.info(LABEL, 'create new article.')
        this.$store.dispatch('types/loadTypes').then(() => {
          this.$store.commit('article/initArticle')
        })
      }
    },
    toggleRightSideBar () {
      this.$store.dispatch('pagestate/toggleRightSideBar')
    },
    releaseArticle () {
      logger.debug(LABEL, JSON.stringify(this.article, null, '  '))
      this.$store.dispatch('article/saveArticle').then(() => {
        if (this.article.draftId) {
          return this.$store.dispatch('article/deleteDraft')
        }
      }).then(() => {
        this.$router.push('/articles/' + this.article.knowledgeId)
      })
    },
    saveDraftArticle () {
      this.$store.dispatch('article/saveDraft').then((id) => {
        logger.debug(LABEL, 'article was save draft. draft id: ' + id)
      })
    },
    deleteDraft () {
      this.$store.dispatch('article/deleteDraft').then(() => {
        logger.debug(LABEL, 'draft is deleted.')
        this.article.draftId = null
      })
    }
  },
  watch: {
    '$route': 'getArticle'
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getArticle()
      // この画面特有の操作ボタンにTips表示
      tippy('[title]', {
        placement: 'bottom',
        animation: 'scale',
        duration: 200,
        arrow: true
      })
      // この画面特有の操作ボタンの固定
      secondNavbar()
    })
  }
}
</script>

<style src="../../css/article-common.css" />
