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

            <div class="box box-default">
              <div class="box-header">
                <h3 class="box-title">
                  <button class="btn btn-primary" v-on:click="addStock">{{$t('Label.Add')}}</button>
                </h3>
                <div class="box-tools">
                  <ul class="pagination pagination-sm no-margin pull-right">
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
                </div>
              </div>
              <!-- /.box-header -->
              <div class="box-body no-padding">
                <table class="table table-striped">
                  <tbody><tr>
                    <th style="width:20px">#</th>
                    <th>{{$t('StockList.Name')}}</th>
                    <th>{{$t('StockList.Note')}}</th>
                    <th>{{$t('StockList.Count')}}</th>
                  </tr>
                  <tr v-for="(item, index) in items" :key="item.stockId" class="clickable"
                    v-on:click="selectStock(item.stockId)">
                    <td>{{index + parseInt(pagination.offset) + 1}}</td>
                    <td>{{item.stockName}}</td>
                    <td>{{item.description}}</td>
                    <td>{{item.knowledgeCount}}</td>
                  </tr>
                </tbody></table>
              </div>
              <!-- /.box-body -->
            </div>



      </div>
    </div>
    <!-- /.content-wrapper -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

export default {
  name: 'StockList',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/stocks/', name: 'Route.StockList'}
      ]
    }
  },
  computed: {
    ...mapState({
      item: state => state.stocks.item,
      items: state => state.stocks.items,
      pagination: state => state.stocks.pagination
    })
  },
  methods: {
    selectPage (offset) {
      this.$store.dispatch('stocks/getStocks', offset)
    },
    getStocks () {
      this.$store.dispatch('stocks/getStocks')
    },
    selectStock (id) {
      this.$store.state.stocks.articles = []
      this.$store.state.stocks.articlePagination = {
        limit: 10,
        offst: 0,
        total: 0,
        next: -1,
        prev: -1,
        pages: []
      }
      this.$router.push('/stocks/' + id + '/articles')
    },
    addStock () {
      this.item.stockName = ''
      this.item.description = ''
      this.$router.push('/stocks/add')
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.getStocks()
    })
  }
}
</script>
