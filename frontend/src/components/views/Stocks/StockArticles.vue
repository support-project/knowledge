<template>
  <div>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <page-title
        :title = "'Route.' + $route.name"
        :description = "$route.name + '.description'" />
      <!-- Main content -->
      <div class="content main-content">


            <div class="box box-default">
              <div class="box-header">
                <h3 class="box-title"><!--Liked users--></h3>
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
  name: 'StockList',
  components: { PageTitle },
  computed: {
    ...mapState({
      items: state => state.stocks.articles,
      pagination: state => state.stocks.articlePagination
    })
  },
  methods: {
    selectPage (offset) {
      this.$store.dispatch('stocks/getArticles', this.$route.params.id, offset)
    },
    getArticles () {
      this.$store.dispatch('stocks/getArticles', this.$route.params.id)
    },
    selectArticle (id) {
      this.$router.push('/articles/' + id)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.getArticles()
    })
  }
}
</script>
