<template>
  <div id="app">
    <i class="fa fa-refresh fa-spin fa-1x fa-fw text-yellow loading-icon" v-show="pagestate.loading"></i>
    <router-view></router-view>
    <transition
      name="custom-classes-transition"
      enter-active-class="animated fadeIn"
      leave-active-class="animated fadeOut"
    >
      <p class="pagetop" v-if="showReturnToTop">
        <a v-on:click="returnToTop()"><i class="fa fa-arrow-up" aria-hidden="true"></i></a>
      </p>
    </transition>
  </div>
</template>

<script>
/* global $ */
import { mapState } from 'vuex'
import { Notification } from 'uiv'

import 'animate.css/animate.min.css'
import './css/returnToTop.css'

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
      section: 'Head',
      showReturnToTop: false
    }
  },
  methods: {
    returnToTop () {
      $('body, html').animate({
        scrollTop: 0
      }, 500)
      return false
    }
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      // トップへ戻る
      $(window).scroll(() => {
        if ($(window).scrollTop() > 130) {
          this.showReturnToTop = true
        } else {
          this.showReturnToTop = false
        }
      })
    })
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

