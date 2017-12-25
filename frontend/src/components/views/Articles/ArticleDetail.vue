<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <!-- Main content -->
    <div class="content">
      #{{ $route.params.id }}

      <i class="fa fa-refresh fa-spin fa-3x fa-fw" v-if="pagestate.loading"></i>


      <router-link tag="a" :to="'/articles/' + $route.params.id + '/edit'">
        <a>
          編集
        </a>
      </router-link>
    

      <span v-html="resources.article.displaySafeHtml"></span>


    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

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
  components: { PageTitle },
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
      })
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getArticle()
    })
  }
}
</script>
