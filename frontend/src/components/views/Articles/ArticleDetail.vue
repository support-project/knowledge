<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="secondNavbarMenu" >
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
        <a id="toc" :title="$t('ArticleDetail.BtnToc')">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
        </a>
      </nav>
    </div>

    <!-- Main content -->
    <div class="content">
      <div class="title"><span class="articleid">#{{ $route.params.id }}</span> {{ resources.article.title}}</div>

      <i class="fa fa-refresh fa-spin fa-3x fa-fw" v-if="pagestate.loading"></i>

      <div class="markdown-body">
        <span v-html="this.resources.article.displaySafeHtml"></span>
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
  components: { PageTitle, ArticleDetailSidebar },
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
.title {
  font-size: 36px;
}
.title .articleid {
  color: rgb(128, 128, 128)
}
</style>
<style src="../../css/secondNavbar.css" />
