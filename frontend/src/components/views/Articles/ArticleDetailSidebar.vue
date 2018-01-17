<template>

<aside class="control-sidebar control-sidebar-dark "
  v-bind:class="{'control-sidebar-open': pagestate.toggleTOC}">
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      <div class="close-btn-on-sidebar">
        <a class="btn btn-link" v-on:click="toggleSideBar()">
          <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
        </a>
      </div>
      <div class="toc-title">
        &lt;{{ $t('ArticleDetailSidebar.TocTitle') }}&gt;
      </div>
      <span v-html="this.resources.toc"></span>
    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ArticleDetailSidebar',
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    toggleSideBar () {
      this.$store.dispatch('toggleTOC')
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
