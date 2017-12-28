<template>

<aside class="control-sidebar control-sidebar-dark control-sidebar-open">
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      <div class="close-btn-on-sidebar">
        <a id="closeSideBar" class="btn btn-link">
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
/* global $ */
import { mapState } from 'vuex'
import toggleSideBar from '../../../lib/displayParts/toggleSideBar'

export default {
  name: 'ArticleDetailSidebar',
  computed: {
    ...mapState([
      'resources'
    ])
  },
  mounted () {
    this.$nextTick(() => {
      $('#closeSideBar').click(() => {
        toggleSideBar.toggle()
      })
    })
  }
}
</script>

<style src="../../css/toc.css" />
<style src="../../css/control-sidebar.css" />
