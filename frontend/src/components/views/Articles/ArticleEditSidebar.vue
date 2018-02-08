<template>

<aside class="control-sidebar control-sidebar-dark"
  v-bind:class="{'control-sidebar-open': pagestate.showRightSideBar}">
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      <div class="close-btn-on-sidebar">
        <a class="btn btn-link" v-on:click="toggleRightSideBar()">
          <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
        </a>
      </div>

      <div class="sidebar-group">
        <span class="sidebar-title">Types:</span>
        <div v-for="t in types" :key="t.id">
          <label v-if="article.type.id">
            <input type="radio" name="type" :value="t.id"
              v-model="article.type.id" v-on:change="changeType(t.id)">
            <i :class="'fa ' + t.icon" aria-hidden="true"></i>
            {{ t.name | abbreviate(25)}}
          </label>
        </div>
      </div>

      <div class="sidebar-group">
        <span class="sidebar-title">Viewer:</span>
        <article-parts-target />
      </div>

    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'
import logger from 'logger'

import ArticlePartsTarget from './ArticlePartsTarget'

const LABEL = 'ArticleEditSidebar.vue'

export default {
  name: 'ArticleEditSidebar',
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      article: state => state.article.article,
      types: state => state.types.types
    })
  },
  components: {ArticlePartsTarget},
  methods: {
    toggleRightSideBar () {
      this.$store.dispatch('pagestate/toggleRightSideBar')
    },
    changeType (type) {
      logger.debug(LABEL, type)
      this.$store.commit('article/changeArticleType', {
        type: type,
        types: this.types
      })
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>
.sidebar-group {
  margin-bottom: 10px;
}
.sidebar-group .sidebar-title {
  margin-bottom: 10px;
}
</style>

<style src="../../css/toc.css" />
<style src="../../css/control-sidebar.css" />
