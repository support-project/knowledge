<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <!-- Main content -->
    <div class="content">
      #{{ $route.params.id }}

      <router-link tag="a" :to="'/articles/' + $route.params.id + '/edit'">
        <a>
          編集
        </a>
      </router-link>
    


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
      'resources'
    ])
  }
}
</script>
