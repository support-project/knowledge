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

      Types:
      <div v-for="t in resources.types" :key="t.id">
        <label v-if="resources.article.type">
          <input type="radio" name="type" :value="t.id" v-model="resources.article.type.id" v-on:change="changeType(t.id)">
          <i :class="'fa ' + t.icon" aria-hidden="true"></i>
          {{ t.name | abbreviate(25)}}
        </label>
      </div>

    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'
import logger from 'logger'

const LABEL = 'ArticleEditSidebar.vue'

export default {
  name: 'ArticleEditSidebar',
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    toggleRightSideBar () {
      this.$store.dispatch('toggleRightSideBar')
    },
    changeType (type) {
      logger.debug(LABEL, type)
      this.$store.commit('CHANGE_ARTICLE_TYPE', type)
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style src="../../css/toc.css" />
<style src="../../css/control-sidebar.css" />
