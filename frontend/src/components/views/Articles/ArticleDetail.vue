<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="slidemenu" >
        <a :title="$t('ArticleDetail.BtnStar')">
          <i class="fa fa-thumbs-o-up fa-lg" aria-hidden="true"></i>
        </a>
        <a :title="$t('ArticleDetail.BtnStock')">
          <i class="fa fa-star-o fa-lg" aria-hidden="true"></i>
        </a>
        <router-link tag="a" :to="'/articles/' + $route.params.id + '/edit'"
          :title="$t('ArticleDetail.BtnEdit')">
          <i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
        </router-link>
        <a id="toc" :title="$t('ArticleDetail.BtnToc')" v-bind:class="{'label pull-right bg-green': pagestate.toggleTOC}">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
        </a>
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

      <div class="markdown-body">
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
import toggleSideBar from '../../../lib/displayParts/toggleSideBar'
import moveTocTarget from '../../../lib/displayParts/moveTocTarget'

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
      console.log('getArticle')
      this.$store.dispatch('getArticle', this.$route.params.id)
      .then(() => {
        console.log('finish getArticle')
        setTimeout(() => {
          processFootnotesPotision($('.markdown-body'))
          moveTocTarget()
        }, 500)
      })
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getArticle()

      tippy('[title]', {
        placement: 'bottom',
        animation: 'scale',
        duration: 200,
        arrow: true
      })

      // 目次のサイドバーのトグル
      toggleSideBar.init(true)
      $('#toc').click(() => {
        toggleSideBar.toggle()
      })

      // この画面特有の操作ボタンの固定
      secondNavbar()
    })
  }
}
</script>

<style>
.content {
  box-sizing: border-box;
  padding-left: 30px;
}
@media (max-width: 767px) {
  .content {
    padding: 10px;
  }
}
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

.left-margin {
  margin-left: 10px;
}

</style>
<style src="../../css/secondNavbar.css" />
