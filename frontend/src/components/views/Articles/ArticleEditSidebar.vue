<template>

<aside class="control-sidebar control-sidebar-dark"
  v-bind:class="{'control-sidebar-open': pagestate.toggleAttributes}">
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      <div class="close-btn-on-sidebar">
        <a class="btn btn-link" v-on:click="toggleAttributes()">
          <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
        </a>
      </div>

      Types:
      <div v-for="t in resources.types" :key="t.id">
        <label v-if="resources.article.type">
          <input type="radio" name="type" :value="t.id" v-model="resources.article.type.id" v-on:change="changeType(t.id)">
          <i :class="'fa ' + t.icon" aria-hidden="true"></i>
          {{ t.name | abbreviate}}
        </label>
      </div>

    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'
import lang from 'lang'
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
  filters: {
    abbreviate: function (value) {
      return lang.abbreviate(value, 25)
    }
  },
  methods: {
    toggleAttributes () {
      this.$store.dispatch('toggleAttributes')
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
