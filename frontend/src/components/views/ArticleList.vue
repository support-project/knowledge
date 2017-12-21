<template>
  <!-- Main content -->
  <div class="content">
    <i class="fa fa-refresh fa-spin fa-3x fa-fw" id="loadingList"></i>

    <ul>
      <li v-for="article in resources.articles" :key="article.id" >
        {{ article.title }}
      </li>
    </ul>



  </div>
</template>

<script>
/* global $ */
import { mapState } from 'vuex'

export default {
  name: 'ArticleListView',
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
