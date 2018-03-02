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
        <i class="fa fa-refresh fa-spin fa-3x fa-fw" id="loadingList"></i>

        <div class="knowledge_item" v-for="draft in drafts" :key="draft.draftId">
          <router-link tag="a" :to="draft.editPage">
            <div class="item-info">
              <a>
              <i class="fa fa-calendar"></i>&nbsp;{{draft.updateDatetime | dispDate}}
              <span class="dispKnowledgeId" v-if="draft.knowledgeId">
                #{{draft.knowledgeId}}
              </span>
              <span v-if="!draft.title">
                {{ $t( 'DraftList.EmptyTitle' ) }}
              </span>
              {{draft.title}}
              </a>
            </div>
            <div class="item-info">
            {{draft.content | abbreviate(80) }}
            </div>
          </router-link>
        </div>
      </div>
    </div>
    <!-- /.content-wrapper -->

  </div>

</template>

<script>
/* global $ */
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

export default {
  name: 'DraftList',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/drafts/', name: 'Route.DraftList'}
      ]
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      drafts: state => state.articles.drafts
    })
  },
  methods: {
    getDrafts () {
      this.$store.dispatch('articles/getDrafts')
      .then(() => {
        $('#loadingList').addClass('hide')
      })
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      this.getDrafts()
    })
  }
}
</script>
<style>
.padding-side {
  padding-left: 10px;
}
</style>
<style src="../../css/knowledge-list.css" />
