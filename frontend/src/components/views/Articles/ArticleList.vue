<template>
  <div>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

      <page-title
        :title = "'Route.' + $route.name"
        :description = "$route.name + '.description'"
        :breadcrumb = "breadcrumb" />

      <!-- Main content -->
      <div class="content main-content">
        <div class="search-condition" v-if="search.available">
          <i class="fa fa-filter" aria-hidden="true"></i>:
          <button class="btn btn-link search-condition-param" v-if="search.keyword">
            <i class="fa fa-search"></i>{{search.keyword}}&nbsp;
          </button><button class="btn btn-link search-condition-param" v-for="t in searchTypes" :key="t.id">
            <i :class="'fa ' + t.icon" aria-hidden="true"></i>{{t.name}}&nbsp;
          </button>
          <button class="btn btn-default btn-sm" v-on:click="clearFilter">
            <i class="fa fa-times-circle" aria-hidden="true"></i>RemoveFilter
          </button>
        </div>

        <div v-if="pagestate.loading" class="text-center">
          <i class="fa fa-refresh fa-spin fa-1x fa-fw" v-if="pagestate.loading"></i>
        </div>

        <!-- Tab -->
        <div class="row padding-side">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="open.knowledge/list">一覧</a></li>
                <li role="presentation"><a href="open.knowledge/show_popularity">人気</a></li>
                <li role="presentation"><a href="open.knowledge/stocks">ストック</a></li>
                <li role="presentation"><a href="open.knowledge/show_history">履歴</a></li>
            </ul>
        </div>

        <div class="row" id="knowledgeList">
          <!-- main list -->
          <div class="col-sm-12 col-md-8 knowledge_list">
            <article-list-item :articles="articles"/>

            <div class="box-tools article-list-pagination">
              <ul class="pagination pagination-sm no-margin">
                <li class="clickable" v-on:click="selectPage(pagination.first)">
                  <a :class="{'disabled': pagination.prev === -1}"><i class="fa fa-step-backward" aria-hidden="true"></i></a>
                </li>
                <li class="clickable" v-on:click="selectPage(pagination.prev)">
                  <a :class="{'disabled': pagination.prev === -1}"><i class="fa fa-backward" aria-hidden="true"></i></a>
                </li>
                <li class="clickable" v-for="page in pagination.pages" :key="page.label"
                  v-on:click="selectPage(page.offset)">
                  <a :class="{'active': page.current}">{{page.label}}</a>
                </li>
                <li class="clickable" v-on:click="selectPage(pagination.next)">
                  <a :class="{'disabled': pagination.next === -1}"><i class="fa fa-forward" aria-hidden="true"></i></a>
                </li>
                <li class="clickable" v-on:click="selectPage(pagination.last)">
                  <a :class="{'disabled': pagination.next === -1}"><i class="fa fa-step-forward" aria-hidden="true"></i></a>
                </li>
              </ul>
              <span class="text-DarkSlateGray article-list-pagination-total">
                {{pagination.offset + 1}}
                -
                {{pagination.offset + articles.length}}
                /
                {{pagination.total}}
              </span>
            </div>
          </div>
          <!-- right area -->
          <div class="col-sm-12 col-md-4">
            <article-list-calendar />
          </div>
        </div>
      </div>
    </div>
    <!-- /.content-wrapper -->

  </div>

</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'
import ArticleListItem from './ArticleListItem'
import ArticleListCalendar from './ArticleListCalendar'

import logger from 'logger'
const LABEL = 'ArticleList.vue'

export default {
  name: 'ArticleList',
  data () {
    let breadcrumb = []
    if (this.$route.path === '/myarticles') {
      breadcrumb.push({to: '/myarticles/' + this.$route.params.id, name: 'Route.MyArticleList'})
    }
    return {
      breadcrumb: breadcrumb,
      searchTypes: []
    }
  },
  components: { PageTitle, ArticleListItem, ArticleListCalendar },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      articles: state => state.articles.articles,
      pagination: state => state.articles.pagination,
      search: state => state.articles.search
    })
  },
  methods: {
    getArticleList () {
      this.$store.dispatch('articles/getArticles')
    },
    selectPage (offset) {
      this.$store.dispatch('articles/selectPage', offset)
    },
    clearSearchCondition () {
      this.$store.commit('articles/clearSearchCondition')
    },
    setMyIdToSearchCondition () {
      logger.trace(LABEL, JSON.stringify(this.$store.getters['user/getUser']))
      var id = this.$store.getters['user/getUser'].userId
      this.$store.commit('articles/setCreatorToSearchCondition', id)
    },
    loadQueryListParam (param) {
      let arr = []
      if (param) {
        let list = param.split(',')
        list.forEach(element => {
          arr.push(element)
        })
      }
      return arr
    },
    loadQueryParams () {
      this.$store.state.articles.search.keyword = this.$route.query.keyword ? this.$route.query.keyword : ''
      this.$store.state.articles.search.types = this.loadQueryListParam(this.$route.query.types)
      this.$store.state.articles.search.typeIds = this.loadQueryListParam(this.$route.query.typeIds)
      this.$store.dispatch('articles/getTypesInformation').then(searchTypes => {
        logger.info(LABEL, JSON.stringify(searchTypes))
        this.searchTypes = searchTypes
      })
    },
    clearFilter () {
      this.$store.state.articles.search.keyword = ''
      this.$store.state.articles.search.types = []
      this.$store.state.articles.search.typeIds = []
      this.getArticleList()
    }
  },
  watch: {
    '$route' (to, from) {
      logger.debug(LABEL, 'from:' + from.path + '   ' + 'to:' + to.path)
      this.breadcrumb = []
      if (from.path !== '/myarticles' && to.path === '/myarticles') {
        this.clearSearchCondition()
        this.setMyIdToSearchCondition()
        this.breadcrumb.push({to: '/myarticles/' + this.$route.params.id, name: 'Route.MyArticleList'})
      } else if (from.path !== '/' && to.path === '/') {
        this.clearSearchCondition()
      } else {
        this.loadQueryParams()
      }
      this.getArticleList()
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      logger.info(LABEL, JSON.stringify(this.$route.query))
      if (this.$route.path === '/') {
        this.clearSearchCondition()
      } else if (this.$route.path === '/myarticles') {
        this.clearSearchCondition()
        this.setMyIdToSearchCondition()
      } else {
        this.loadQueryParams()
      }
      this.getArticleList()
    })
  }
}
</script>
<style>
.padding-side {
  padding-left: 10px;
}
.article-list-pagination {
  margin-top: 20px;
  width: 100%;
  text-align: center;
  vertical-align: middle;
}
.article-list-pagination .article-list-pagination-total {
  font-size: 80%;
  position: relative;
  bottom: 10px;
}
.search-condition {
  margin-top: -10px;
  margin-bottom: 10px;
  border: 0px solid black;
}
.search-condition-param {
  border: 0px solid black;
  padding: 0px;
  margin-right: 2px;
}
</style>
<style src="../../css/knowledge-list.css" />
