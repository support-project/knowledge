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

      属性の編集がサイドバーで実施

    </div>
  </div>
</aside>
  
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ArticleEditSidebar',
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  methods: {
    toggleAttributes () {
      this.$store.dispatch('toggleAttributes')
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
