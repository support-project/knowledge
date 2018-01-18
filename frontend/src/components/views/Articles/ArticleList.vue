<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'" />

    <!-- Main content -->
    <div class="content">
      <i class="fa fa-refresh fa-spin fa-3x fa-fw" id="loadingList"></i>

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
          <article-list-item :articles="resources.articles"/>
        </div>
        <!-- right area -->
        <div class="col-sm-12 col-md-4">
          <article-list-calendar />
        </div>
      </div>
    </div>
  </div>

</template>

<script>
/* global $ */
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'
import ArticleListItem from './ArticleListItem'
import ArticleListCalendar from './ArticleListCalendar'

export default {
  name: 'ArticleList',
  components: { PageTitle, ArticleListItem, ArticleListCalendar },
  computed: {
    ...mapState([
      'resources'
    ])
  },
  methods: {
    getArticleList () {
      this.$store.dispatch('getArticles')
      .then(() => {
        $('#loadingList').addClass('hide')
      })
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getArticleList()
    })
  }
}
</script>
<style>
.padding-side {
  padding-left: 10px;
}
</style>
<style src="../../css/knowledge-list.css" />
