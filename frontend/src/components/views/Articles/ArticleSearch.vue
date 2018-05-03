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
        <div class="box box-primary">
          <div class="box-header with-border">
            <h3 class="box-title">{{$t("ArticleSearch.SearchCondition")}}</h3>
          </div>
            <!-- /.box-header -->
            <!-- form start -->
            <form role="form" v-on:submit.prevent="doSearchArticles()">
              <div class="box-body">
                <div class="form-group">
                  <label for="keyword">{{$t("ArticleSearch.Keyword")}}</label>
                  <input type="text" class="form-control" name="keyword" id="keyword" :placeholder="$t('ArticleSearch.Keyword')" v-model="keyword">
                </div>

                <div class="form-group">
                  <label for="Type">{{$t("ArticleSearch.Types")}}</label><br/>
                  <label v-for="t in typeItems" :key="t.id" class="article-search-condition-type">
                    <input type="checkbox" name="type" :value="t" v-model="types">
                    <i :class="'fa ' + t.icon" aria-hidden="true"></i>
                    {{ t.name | abbreviate(20)}}
                  </label>
                </div>
                

              </div>
              <!-- /.box-body -->
              <div class="box-footer">
                <button type="submit" class="btn btn-primary">Submit</button>
              </div>
            </form>
          </div>
      </div>
    </div>
    <!-- /.content-wrapper -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

import logger from 'logger'
const LABEL = 'ArticleSearch.vue'

export default {
  name: 'ArticleSearch',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/articles/search' + this.$route.params.id, name: 'Route.ArticleSearch'}
      ],
      keyword: '',
      types: []
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      typeItems: state => state.types.types
    })
  },
  methods: {
    doSearchArticles () {
      let params = {}
      if (this.keyword) {
        params.keyword = this.keyword
      }
      if (this.types && this.types.length > 0) {
        this.types.forEach(t => {
          if (!params.types) params.types = []
          params.types.push(t.name)
          if (!params.typeIds) params.typeIds = []
          params.typeIds.push(t.id)
        })
      }
      logger.info(LABEL, JSON.stringify(params))
      let query = ''
      Object.entries(params).forEach(e => {
        if (query) query += '&'
        query += e[0] + '='
        if (Array.isArray(e[1])) {
          query += e[1].join(',')
        } else {
          query += e[1]
        }
      })
      let route = '/articles'
      if (query) {
        route += '?' + query
      }
      this.$router.push(route)
    }
  },
  mounted () {
    this.$nextTick(() => {
      this.$store.dispatch('types/loadTypes')
    })
  }
}
</script>
<style>
.article-search-condition-type {
  margin-right: 8px;
}
</style>
