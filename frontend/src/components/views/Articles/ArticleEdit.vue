<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <!-- Main content -->
    <div class="content">
      #{{ $route.params.id }}
      Edit
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
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'},
        {to: '/articles/' + this.$route.params.id + '/edit', name: 'Route.ArticleEdit'}
      ]
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
