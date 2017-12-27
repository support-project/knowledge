<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="slidemenu" >
        <a ><i class="fa fa-thumbs-o-up fa-lg" aria-hidden="true"></i></a>
        <a ><i class="fa fa-star-o fa-lg" aria-hidden="true"></i></a>
        <router-link tag="a" :to="'/articles/' + $route.params.id + '/edit'">
          <i class="fa fa-pencil-square-o fa-lg" aria-hidden="true"></i>
        </router-link>
        <a id="toc"><i class="fa fa-list fa-lg" aria-hidden="true"></i></a>
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
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'
import ArticleDetailSidebar from './ArticleDetailSidebar'

import processFootnotesPotision from '../../../lib/decorateMarkdown/processFootnotesPotision'

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
        }, 500)
      })
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getArticle()

      var toggle = false
      $('#toc').click(() => {
        if (!toggle) {
          $('.control-sidebar').addClass('control-sidebar-open')
//          $('.control-sidebar').addClass('fixed')
          toggle = true
        } else {
          $('.control-sidebar').removeClass('control-sidebar-open')
//          $('.control-sidebar').removeClass('fixed')
          toggle = false
        }
      })
      var nav = $('#secondNavbar')
      var offset = nav.offset()
      $(window).scroll(function () {
        if ($(window).scrollTop() > offset.top) {
          nav.addClass('fixed')
          nav.addClass('navbar-color')
        } else {
          nav.removeClass('fixed')
          nav.removeClass('navbar-color')
        }
      })
    })
  }
}
</script>

<style>
#secondNavbar {
	width: 100%;
}
.slidemenu {
  height: 50px;
  display: flex;
  /*justify-content: center;*/
  align-items: center;
}
.slidemenu a, .slidemenu a:hover, .slidemenu a:active {
  margin-left: 10px;
  height: 40px;
  width: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 30px;
  -webkit-border-radius: 30px;
  -moz-border-radius: 30px;
  cursor: pointer;
}

.fixed {
  position: fixed;
  top: 0;
  z-index: 999;
}

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
