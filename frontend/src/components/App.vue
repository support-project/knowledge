<template>
  <div id="app">
    <i class="fa fa-refresh fa-spin fa-1x fa-fw text-yellow loading-icon" v-if="pagestate.loading"></i>
    <router-view></router-view>
  </div>
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: 'App',
  computed: {
    ...mapState([
      'pagestate'
    ])
  },
  data () {
    return {
      section: 'Head'
    }
  },
  methods: {
  }
}
</script>

<style>
.loading-icon {
  position: fixed;
  bottom: 0px;
  left: 0px;
  z-index: 9998;
}
</style>

