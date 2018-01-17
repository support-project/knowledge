<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="secondMenu" >
        <a :title="$t('ArticleDetail.BtnToc')" v-bind:class="{'toggle-on': pagestate.toggleTOC}"
          v-on:click="toggleSideBar()">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
        </a>
        <button :title="$t('ArticleDetail.BtnStar')">
          <i class="fa fa-thumbs-o-up fa-lg" aria-hidden="true"></i>
        </button>
        <a :title="$t('ArticleDetail.BtnStock')">
          <i class="fa fa-star-o fa-lg" aria-hidden="true"></i>
        </a>
        <router-link tag="a" :to="'/articles/' + $route.params.id + '/edit'"
          :title="$t('ArticleDetail.BtnEdit')">
          <i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
        </router-link>
      </nav>
    </div>

    <!-- Main content -->
    <div class="content">
      <div class="article-information">
        <div class="article-title"><span class="article-id">#{{ $route.params.id }}</span> {{ resources.article.title}}</div>
        
        <div class="article-meta">
          <article-parts-editor :article="resources.article" />
        </div>
        <div class="article-meta">
          <article-parts-point :article="resources.article" />
        </div>
        <div class="article-meta">
          <article-parts-type-label :article="resources.article" />
          <span class="left-margin">
            <article-parts-public-flag :article="resources.article" />
          </span>
        </div>
        <div class="article-meta">
          <article-parts-tags :article="resources.article" />
          <article-parts-stocks :article="resources.article" />
        </div>
      </div>
      <i class="fa fa-refresh fa-spin fa-3x fa-fw" v-if="pagestate.loading"></i>

      <div class="markdown-body template-items" v-if="this.resources.article.itemsHtml">
        <span v-html="this.resources.article.itemsHtml"></span>
      </div>
      <div class="markdown-body contents">
        <span v-html="this.resources.article.displaySafeHtml"></span>
      </div>
      <div id="comments">
        <article-detail-comments :comments="resources.comments" :article="resources.article" />
      </div>

      <article-detail-sidebar />

    </div>
  </div>
</template>

<script>
/* global $ */
import tippy from 'tippy.js'
import { mapState } from 'vuex'
import logger from 'logger'

import PageTitle from '../Parts/PageTitle'
import ArticleDetailSidebar from './ArticleDetailSidebar'
import ArticlePartsEditor from './ArticlePartsEditor'
import ArticlePartsPoint from './ArticlePartsPoint'
import ArticlePartsTypeLabel from './ArticlePartsTypeLabel'
import ArticlePartsPublicFlag from './ArticlePartsPublicFlag'
import ArticlePartsTags from './ArticlePartsTags'
import ArticlePartsStocks from './ArticlePartsStocks'
import ArticleDetailComments from './ArticleDetailComments'

import processFootnotesPotision from '../../../lib/displayParts/processFootnotesPotision'
import secondNavbar from '../../../lib/displayParts/secondNavbar'
import moveTocTarget from '../../../lib/displayParts/moveTocTarget'
import rightSidebar from './../../../lib/displayParts/rightSidebar'

const LABEL = 'ArticleDetail.vue'

export default {
  name: 'ArticleDetail',
  data () {
    return {
      breadcrumb: [
//        {to: '/articles', name: 'Route.ArticleList'},
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'}
      ]
    }
  },
  watch: {
    '$route' (to, from) {
      // ルートの変更の検知...
    }
  },
  components: { PageTitle, ArticleDetailSidebar, ArticlePartsPoint, ArticlePartsEditor, ArticlePartsTypeLabel, ArticlePartsPublicFlag, ArticlePartsTags, ArticlePartsStocks, ArticleDetailComments },
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    getArticle () {
      logger.debug(LABEL, 'getArticle')
      this.$store.dispatch('getArticle', this.$route.params.id)
      .then(() => {
        logger.debug(LABEL, 'finish getArticle')
        setTimeout(() => {
          processFootnotesPotision($('.markdown-body'))
          moveTocTarget()
        }, 500)
      })
    },
    toggleSideBar () {
      this.$store.dispatch('toggleTOC')
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      // データ読み込み
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
      // 右側のサイドバーを開く
      rightSidebar(true)
    })
  }
}
</script>

<style>
.article-information {
  background: #eaf3ff;
  padding-left: 1vw;
  padding-bottom: 5px;
  border-bottom: solid 3px #516ab6;
  border-left: solid 10px #516ab6;
  margin-bottom: 20px;
}

.article-information .article-title {
  font-size: 32px;
  color: #010101;
  margin-bottom: 10px;
}

.article-information .article-title .article-id {
  color: rgb(128, 128, 128)
}

.article-meta {
  margin-bottom: 3px;
}

.template-items {
  border-bottom: 1px dashed #96cef1;
  padding-left: 20px;
  padding-bottom: 5px;
  margin-bottom: 20px;
}

.markdown-body.contents {
  background-color: white;
  border: 1px solid #ddd;
  padding: 20px;
  border-radius: 10px;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
}

.left-margin {
  margin-left: 10px;
}

</style>
<style src="../../css/secondNavbar.css" />
<style src="../../css/article-common.css" />
