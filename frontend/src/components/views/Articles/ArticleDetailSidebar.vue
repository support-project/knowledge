<template>
  <div>
    <aside class="control-sidebar control-sidebar-dark control-sidebar-fixed"
      v-bind:class="{'control-sidebar-open': pagestate.showRightSideBar}">
      <div class="tab-content" id="tab-content">
        <!-- Home tab content -->
        <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
          <div class="close-btn-on-sidebar">
            <a class="btn btn-link" v-on:click="toggleRightSideBar()">
              <i class="fa fa-angle-double-right fa-2x" aria-hidden="true"></i>
            </a>
          </div>
          <!--
          <div class="toc-title">
            &lt;{{ $t('ArticleDetailSidebar.TocTitle') }}&gt;
          </div>
          <span v-html="this.toc"></span>
          -->

          <div class="box box-info">
            <div class="box-header with-border">
              <h3 class="box-title">{{ $t('ArticleDetailSidebar.TocTitle') }}</h3>
            </div>
            <div class="box-body text-black">
              <span v-html="this.toc"></span>
            </div>
          </div>

        </div>
      </div>
    </aside>

    <div class="control-sidebar-bg"></div>
  </div>
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

<style>
.control-sidebar-fixed {
  position: fixed;
}
</style>

<style src="../../css/toc.css" />
<style src="../../css/control-sidebar.css" />
