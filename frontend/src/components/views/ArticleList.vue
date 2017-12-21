<template>
  <!-- Main content -->
  <div class="content">

    <i class="fa fa-refresh fa-spin fa-3x fa-fw" id="loadingList"></i>

  </div>
</template>

<script>
/* global $ */
  export default {
    name: 'ArticleListView',
    methods: {
      handleClick_changeLanguage (lang) {
        this.$i18n.locale = lang
      },
      getArticleList () {
        console.log('getArticleList')
        setTimeout(() => {
          $('#loadingList').addClass('hide')
          console.log('finish')
        }, 3000)
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
