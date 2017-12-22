<template>
  <!-- Main content -->
  <div class="content">
    <i class="fa fa-refresh fa-spin fa-3x fa-fw" id="loadingList"></i>

    <!-- Tab -->
    <div class="row padding-side">
        <ul class="nav nav-tabs">
            <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/open.knowledge/list">一覧</a></li>
            <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_popularity">人気</a></li>
            <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/stocks">ストック</a></li>
            <li role="presentation"><a href="<%=request.getContextPath()%>/open.knowledge/show_history">履歴</a></li>
        </ul>
    </div>

    <div class="row" id="knowledgeList">
      <!-- main list -->
      <div class="col-sm-12 col-md-8 knowledge_list">
        <article-list-item :articles="resources.articles"/>
      </div>
      <!-- right area -->
      <div class="col-sm-12 col-md-4">
      </div>
    </div>
    <link rel="stylesheet" href="static/css/knowledge-list.css" >
  </div>
</template>

<script>
/* global $ */
import { mapState } from 'vuex'
import ArticleListItem from './ArticleListItem'

export default {
  name: 'ArticleListView',
  components: { ArticleListItem },
  computed: {
    ...mapState([
      'resources'
    ])
  },
  methods: {
    handleClick_changeLanguage (lang) {
      this.$i18n.locale = lang
    },
    getArticleList () {
      console.log('getArticleList')
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
