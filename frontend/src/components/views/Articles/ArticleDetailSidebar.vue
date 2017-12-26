<template>

<aside class="control-sidebar control-sidebar-dark">
  <!-- Create the tabs -->
  <!--
  <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
    <li class="active">
      <a href="#control-sidebar-theme-demo-options-tab" data-toggle="tab"><i class="fa fa-wrench"></i></a></li>
    <li>
      <a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a>
    </li>
    <li>
      <a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a>
    </li>
  </ul>
  -->
  <!-- Tab panes -->
  <div class="tab-content" id="tab-content">
    <!-- Home tab content -->
    <div class="tab-pane active" id="control-sidebar-theme-demo-options-tab">
      aaa
      <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
    </div>
    <!-- Home tab content -->
    <div class="tab-pane" id="control-sidebar-home-tab">
      <h3 class="control-sidebar-heading">Recent Activity</h3>
      <ul class="control-sidebar-menu">
        <li>
          <a href="javascript:void(0)">
            <i class="menu-icon fa fa-birthday-cake bg-red"></i>

            <div class="menu-info">
              <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

              <p>Will be 23 on April 24th</p>
            </div>
          </a>
        </li>
        <li>
          <a href="javascript:void(0)">
            <i class="menu-icon fa fa-user bg-yellow"></i>

            <div class="menu-info">
              <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>

              <p>New phone +1(800)555-1234</p>
            </div>
          </a>
        </li>
      <!-- /.control-sidebar-menu -->
      </ul>
    </div>
  </div>
</aside>
  
</template>

<script>
/* global $ */
export default {
  mounted () {
    var nav = $('#tab-content')
    var offset = nav.offset()
    $(window).scroll(function () {
      if ($(window).scrollTop() > offset.top) {
//        nav.addClass('fixed-control-sidebar')
      } else {
//        nav.removeClass('fixed-control-sidebar')
      }
    })
  }
}
</script>

<style>
.control-sidebar {
  position: fixed;
}
.control-sidebar .tab-content .tab-pane {
  height: 100vh;
  width: 100%;
}
/*
.fixed-control-sidebar {
  width: 229px;
  background-color: blue;
  position: fixed;
  top: 0;
}
*/

</style>
