<template>
  <div>
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
        <a :title="$t('ArticleEdit.BtnDraft')">
          <i class="fa fa-save fa-lg" aria-hidden="true"></i>
        </a>
        <router-link tag="a" :to="'/articles/' + $route.params.id"
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
                v-model="resources.article.title" />
            </div>
        </div>

        <article-edit-items />

        <article-edit-contents :article="resources.article" :rows="20" />

      </form>
      <article-edit-sidebar />

    </div>
  </div>
</template>

<script>
import tippy from 'tippy.js'
import { mapState } from 'vuex'
import logger from 'logger'

import PageTitle from '../Parts/PageTitle'
import ArticleEditSidebar from './ArticleEditSidebar'
import ArticleEditContents from './ArticleEditContents'
import ArticleEditItems from './ArticleEditItems'

import Alerts from '../Parts/Alerts'
import secondNavbar from '../../../lib/displayParts/secondNavbar'
import rightSidebar from './../../../lib/displayParts/rightSidebar'

const LABEL = 'ArticleEdit.vue'

export default {
  name: 'ArticleEdit',
  data () {
    var breadcrumb = [
      {to: '/articles/new', name: 'Route.ArticleCreate'}
    ]
    if (this.$route.params.id) {
      breadcrumb = [
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'},
        {to: '/articles/' + this.$route.params.id + '/edit', name: 'Route.ArticleEdit'}
      ]
    }
    return {
      breadcrumb: breadcrumb
    }
  },
  components: { PageTitle, ArticleEditSidebar, ArticleEditContents, ArticleEditItems, Alerts },
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    getArticle () {
      logger.debug(LABEL, 'getArticle')
      // 右側のサイドバーの状態を復元
      rightSidebar(this.$store.state.pagestate.showRightSideBar)
      // データの取得
      this.$store.dispatch('getArticleForEdit', this.$route.params.id).then(() => {
        return this.$store.dispatch('getTypes')
      })
    },
    toggleRightSideBar () {
      this.$store.dispatch('toggleRightSideBar')
    },
    releaseArticle () {
      logger.debug(LABEL, JSON.stringify(this.resources.article, null, '  '))
      this.$store.dispatch('saveArticle').then((id) => {
        this.$router.push('/articles/' + id)
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
