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


      </div>
    </div>
    <!-- /.content-wrapper -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

export default {
  name: 'StockAdd',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/stocks/', name: 'Route.StockList'},
        {to: '/stocks/add', name: 'Route.StockAdd'}
      ]
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate
    })
  },
  methods: {
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
