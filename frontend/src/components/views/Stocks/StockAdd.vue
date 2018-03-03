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

        <form role="form" v-on:submit.prevent="saveStock()">
          
          <div class="form-group">
            <label>
              {{$t('Stock.Name')}}
            </label>
            <div class="input-group">
              <span class="input-group-addon">
                <span class="fa fa-folder-o"></span>
              </span>
              <input type="text" class="form-control" name="stockName" :placeholder="$t('Stock.Placeholder.Name')"
                v-model="item.stockName" />
            </div>
          </div>

          <div class="form-group">
            <label>
              {{$t('Stock.Description')}}
            </label>
            <div class="input-group">
              <span class="input-group-addon">
                <span class="fa fa-comment-o"></span>
              </span>
              <input type="text" class="form-control" name="description" :placeholder="$t('Stock.Placeholder.Description')"
                v-model="item.description" />
            </div>
          </div>

          <button type="submit" class="btn btn-primary btn-lg">{{$t('Label.Save')}}</button>
          <router-link to="/stocks" class="btn btn-default" tag="a">{{$t('Label.Back')}}</router-link>

        </form>

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
      item: state => state.stocks.item
    })
  },
  methods: {
    saveStock () {
      this.$store.dispatch('stocks/saveArticle').then((stock) => {
        this.$router.push('/stocks/' + stock.stockId + '/articles')
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
