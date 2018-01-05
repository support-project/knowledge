<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="secondMenu" >
        <button :title="$t('ArticleEdit.BtnRelease')" class="label-primary">
          <i class="fa fa-rocket fa-lg" aria-hidden="true"></i>
        </button>
        <a :title="$t('ArticleEdit.BtnDraft')">
          <i class="fa fa-save fa-lg" aria-hidden="true"></i>
        </a>
        <a :title="$t('ArticleEdit.BtnAttributes')"
          v-bind:class="{'toggle-on': pagestate.toggleAttributes}"
          v-on:click="toggleAttributes()">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
        </a>
        <router-link tag="a" :to="'/articles/' + $route.params.id" class="bg-gray"
          :title="$t('ArticleEdit.BtnCancel')">
          <i class="fa fa-undo fa-lg" aria-hidden="true"></i>
        </router-link>
        <a :title="$t('ArticleEdit.BtnRemove')" class="label-danger">
          <i class="fa fa-remove fa-lg" aria-hidden="true"></i>
        </a>
      </nav>
    </div>

    <!-- Main content -->
    <div class="content">
      <form role="form" id="knowledgeForm">
        <div class="form-group">
            <label for="input_title">
              Title
            </label>
            <input type="text" class="form-control" name="title" placeholder="Title"
              v-model="resources.article.title" />
        </div>

        <article-edit-contents :article="resources.article" />

      </form>
      <article-edit-sidebar />

    </div>
  </div>
</template>

<script>
import tippy from 'tippy.js'
import { mapState } from 'vuex'

import PageTitle from '../Parts/PageTitle'
import ArticleEditSidebar from './ArticleEditSidebar'
import ArticleEditContents from './ArticleEditContents'

import secondNavbar from '../../../lib/displayParts/secondNavbar'

export default {
  name: 'ArticleDetail',
  data () {
    return {
      breadcrumb: [
//        {to: '/articles', name: 'Route.ArticleList'},
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'},
        {to: '/articles/' + this.$route.params.id + '/edit', name: 'Route.ArticleEdit'}
      ]
    }
  },
  components: { PageTitle, ArticleEditSidebar, ArticleEditContents },
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    getArticle () {
      this.$store.dispatch('getArticleForEdit', this.$route.params.id)
    },
    toggleAttributes () {
      this.$store.dispatch('toggleAttributes')
    }
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
