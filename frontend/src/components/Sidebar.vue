<template>
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar user panel (optional) -->
      <div class="user-panel">
        <div class="pull-left image">
          <img :src="user.avatar" />
        </div>
        <div class="pull-left info">
          <div>
            <p class="white">{{ user.userName }}</p>
          </div>
          <a href="javascript:;">
            <i class="fa fa fa-heart-o text-success"></i> &#x00D7; {{animated_number}}
          </a>
        </div>
      </div>

      <!-- search form (Optional) -->
      <!--
      <form v-on:submit.prevent class="sidebar-form">
        <div class="input-group">
          <input type="text"
            name="search"
            id="search"
            class="search form-control"
            data-toggle="hideseek" p
            laceholder="Search Menus"
            data-list=".sidebar-menu">
          <span class="input-group-btn">
            <button type="submit" name="search" id="search-btn" class="btn btn-flat">
              <i class="fa fa-search"></i>
            </button>
          </span>
        </div>
      </form>
      -->
      <!-- /.search form -->

      <!-- Sidebar Menu -->
      <sidebar-menu :user="user" />
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
</template>
<script>
import SidebarMenu from './SidebarMenu'
import { mapState } from 'vuex'

export default {
  name: 'Sidebar',
  computed: {
    ...mapState({
      user: state => state.user.user,
      point: state => state.user.user.POINT
    })
  },
  data: function () {
    return {
      animated_number: 0
    }
  },
  watch: {
    point: function (newValue, oldValue) {
      console.log(newValue + ' <- ' + oldValue)
      let timeCnt = 0
      let timer
      const animate = () => {
        timeCnt++
        if (timeCnt <= 60) {
          this.animated_number = Math.floor((newValue - oldValue) * timeCnt / 60) + oldValue
          timer = setTimeout(() => {
            animate()
          }, 10)
        } else {
          clearTimeout(timer)
          timer = null
          this.animated_number = newValue
        }
      }
      animate()
    }
  },
  components: { SidebarMenu },
  mounted: function () {
    window.jQuery('[data-toggle="hideseek"]').off().hideseek()
    this.$nextTick(() => {
      this.animated_number = this.point
    })
  }
}
</script>
<style>
  .user-panel .image img {
    border-radius: 50%;
  }
</style>
