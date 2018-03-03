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

        <div v-if="!edit">
          <div class="row">
            <div class="col-xs-4 col-sm-2">{{$t('Stock.Name')}}</div>
            <div class="col-xs-8 col-sm-10">{{item.stockName}}</div>
          </div>
          <div class="row">
            <div class="col-xs-4 col-sm-2">{{$t('Stock.Description')}}</div>
            <div class="col-xs-8 col-sm-10">{{item.description}}</div>
          </div>
          <button type="button" class="btn btn-default" v-on:click="edit = true">
            {{$t('Label.Edit')}}
          </button>
          <router-link to="/stocks" class="btn btn-default" tag="a">{{$t('Label.Back')}}</router-link>
        </div>

        <div v-if="edit">
          <form role="form" v-on:submit.prevent="updateStock()">
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
            <button type="button" class="btn btn-default" v-on:click="edit = false">
              {{$t('Label.Back')}}
            </button>
          </form>
        </div>

        <div class="box box-default margin-top-10">
          <div class="box-header">
            <h3 class="box-title">
              <i class="fa fa-bookmark-o" aria-hidden="true"></i>
              {{$t('ストックに入っている記事')}}
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
                <th style="width:40%">{{$t('StockArticles.ArticleTitle')}}</th>
                <th style="width:40%">{{$t('StockArticles.Comment')}}</th>
                <th style="width:100px">{{$t('StockArticles.DateTime')}}</th>
              </tr>
              <tr v-for="(item) in items" :key="item.knowledgeId" class="clickable"
                v-on:click="selectArticle(item.knowledgeId)">
                <td>{{item.knowledgeId}}</td>
                <td>{{item.title}}</td>
                <td>{{item.comment}}</td>
                <td>{{item.insertDatetime | dispDate}}</td>
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
  name: 'StockArticles',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/stocks/', name: 'Route.StockList'},
        {to: '/stocks/' + this.$route.params.id, name: 'Route.StockArticles'}
      ],
      edit: false
    }
  },
  computed: {
    ...mapState({
      item: state => state.stocks.item,
      items: state => state.stocks.articles,
      pagination: state => state.stocks.articlePagination
    })
  },
  methods: {
    selectPage (offset) {
      return this.$store.dispatch('stocks/getArticles', this.$route.params.id, offset)
    },
    getArticles () {
      return this.$store.dispatch('stocks/getStock', this.$route.params.id).then(() => {
        return this.$store.dispatch('stocks/getArticles', this.$route.params.id)
      })
    },
    selectArticle (id) {
      this.$router.push('/articles/' + id)
    },
    updateStock () {
      this.$store.dispatch('stocks/saveArticle', this.$route.params.id).then((stock) => {
        this.edit = false
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.getArticles()
    })
  }
}
</script>

<style>
.margin-top-10 {
  margin-top: 10px;
}
</style>

