<template>
  <div id="app">
    <i class="fa fa-refresh fa-spin fa-1x fa-fw text-yellow loading-icon" v-if="pagestate.loading"></i>
    <router-view></router-view>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { Notification } from 'uiv'
export default {
  name: 'App',
  computed: {
    ...mapState([
      'pagestate'
    ]),
    currentAlerts () { return this.$store.state.pagestate.alerts }
  },
  watch: {
    // use the watch to react on the value change.
    currentAlerts (alerts) {
      console.log('alerts was change. ' + JSON.stringify(alerts))
      alerts.forEach(element => {
        if (element.notify) {
          Notification.notify({
            type: element.type,
            title: element.title,
            content: element.content
          })
          element.notify = false
        }
      })
    }
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

