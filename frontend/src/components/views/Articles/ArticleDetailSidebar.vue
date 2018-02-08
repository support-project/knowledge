<template>

<aside class="control-sidebar control-sidebar-dark "
  v-bind:class="{'control-sidebar-open': pagestate.showRightSideBar}">
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      <div class="close-btn-on-sidebar">
        <a class="btn btn-link" v-on:click="toggleRightSideBar()">
          <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
        </a>
      </div>
      <div class="toc-title">
        &lt;{{ $t('ArticleDetailSidebar.TocTitle') }}&gt;
      </div>
      <span v-html="this.toc"></span>
    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ArticleDetailSidebar',
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      toc: state => state.article.toc
    })
  },
  methods: {
    toggleRightSideBar () {
      this.$store.dispatch('pagestate/toggleRightSideBar')
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
