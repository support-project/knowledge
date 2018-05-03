<template>
  <div :class="['wrapper', classes]" style="height: auto; min-height: 100%;">
    <header class="main-header">

      <a href="/" class="logo">
        <!-- mini logo for sidebar mini 50x50 pixels -->
        <span class="logo-mini"><i class="fa fa-book" aria-hidden="true"></i></span>
        <!-- logo for regular state and mobile devices -->
        <span class="logo-lg"><i class="fa fa-book" aria-hidden="true"></i>&nbsp;Knowledge</span>
      </a>

      <!-- Header Navbar -->
      <nav class="navbar navbar-static-top" role="navigation">
        <!-- Sidebar toggle button-->
        <a href="javascript:;" class="sidebar-toggle" data-toggle="offcanvas" role="button">
          <span class="sr-only">Toggle navigation</span>
        </a>
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
          <ul class="nav navbar-nav">

            <!-- search -->
            <li>
            <form v-on:submit.prevent="searchArticles()" class="sidebar-form form-inline search-form">
              <div class="input-group">
                <input type="text"
                  name="search"
                  id="search"
                  class="search form-control"
                  placeholder="Keyword"
                  v-model="keyword"
                >
                <span class="input-group-btn">
                  <button type="submit" name="search" id="search-btn" class="btn btn-flat" >
                    <i class="fa fa-search"></i>
                  </button>
                </span>
              </div>
            </form>
            </li>

            <!-- Messages-->
            <li class="dropdown messages-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-envelope-o"></i>
                <span class="label label-success">{{ userInfo.messages | count }}</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have {{ userInfo.messages | count }} message(s)</li>
                <li v-if="userInfo.messages.length > 0">
                  <!-- inner menu: contains the messages -->
                  <ul class="menu">
                    <li>
                      <!-- start message -->
                      <a href="javascript:;">
                        <!-- Message title and timestamp -->
                        <h4>
                          Support Team
                          <small>
                            <i class="fa fa-clock-o"></i> 5 mins</small>
                        </h4>
                        <!-- The message -->
                        <p>Why not consider this a test message?</p>
                      </a>
                    </li>
                    <!-- end message -->
                  </ul>
                  <!-- /.menu -->
                </li>
                <li class="footer" v-if="userInfo.messages.length > 0">
                  <a href="javascript:;">See All Messages</a>
                </li>
              </ul>
            </li>
            <!-- /.messages-menu -->
  
            <!-- Notifications Menu -->
            <li class="dropdown notifications-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <i class="fa fa-bell-o"></i>
                <span class="label label-warning">{{ userInfo.notifications | count }}</span>
              </a>
              <ul class="dropdown-menu">
                <li class="header">You have {{ userInfo.notifications | count }} notification(s)</li>
                <li v-if="userInfo.notifications.length > 0">
                  <!-- Inner Menu: contains the notifications -->
                  <ul class="menu">
                    <li>
                      <!-- start notification -->
                      <a href="javascript:;">
                        <i class="fa fa-users text-aqua"></i> 5 new members joined today
                      </a>
                    </li>
                    <!-- end notification -->
                  </ul>
                </li>
                <li class="footer" v-if="userInfo.notifications.length > 0">
                  <a href="javascript:;">View all</a>
                </li>
              </ul>
            </li>
            
            <!-- User Account Menu -->
            <li class="dropdown user user-menu">
              <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown">
                <!-- The user image in the navbar-->
                <img v-bind:src="user.avatar" class="user-image" alt="User Image">
                <!-- hidden-xs hides the username on small devices so only the image appears. -->
                <span class="hidden-xs">&nbsp;</span>
              </a>
            </li>
          </ul>
        </div>
      </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <sidebar />

    <!-- contents -->
    <router-view></router-view>
  
    <!-- Main Footer -->
    <footer class="main-footer">
      <ul class="footer-menu list-inline">
        <li class="first">
            <a class="" href="" style="cursor: pointer;">
              {{ $t('DashView.about') }}
            </a>
        </li>
        <li>
            <a class="" href="https://information-knowledge.support-project.org/manual" style="cursor: pointer;">
              {{ $t('DashView.manual') }}
            </a>
        </li>
        <li>
            <a class="" href="open.license" style="cursor: pointer;">
              {{ $t('DashView.licence') }}
            </a>
        </li>
      </ul>
      <strong>Copyright &copy; 2015 - {{year}}
        <a href="https://support-project.org/knowledge_info/index">support-project.org</a>.</strong> All rights reserved.
    </footer>
  </div>
  <!-- ./wrapper -->
</template>

<script>
import { mapState } from 'vuex'
import config from '../config'
import Sidebar from './Sidebar'
import 'hideseek'

import logger from 'logger'
const LABEL = 'Dash.vue'

export default {
  name: 'Dash',
  components: {
    Sidebar
  },
  data: function () {
    return {
      // section: 'Dash',
      year: new Date().getFullYear(),
      classes: {
        fixed_layout: config.fixedLayout,
        hide_logo: config.hideLogoOnMobile
      },
      error: '',
      keyword: ''
    }
  },
  computed: {
    ...mapState({
      pagestate: state => state.pagestate,
      user: state => state.user.user,
      userInfo: state => state.user.userInfo,
      search: state => state.articles.search
    })
  },
  methods: {
    changeloading () {
      this.$store.commit('TOGGLE_SEARCHING')
    },
    searchArticles () {
      logger.debug(LABEL, 'searchArticles:' + this.keyword)
      this.$router.push('/articles?keyword=' + this.keyword)
      /*
      logger.debug(LABEL, 'searchArticles:' + this.$route.path)
      if (this.$route.path !== '/' && this.$route.path !== '/myarticles') {
        this.$router.push('/')
      } else {
        this.$store.dispatch('articles/getArticles')
      }
      */
    }
  }
}
</script>

<style lang="scss">
.wrapper.fixed_layout {
  .main-header {
    position: fixed;
    width: 100%;
  }

  .content-wrapper {
    padding-top: 50px;
  }

  .main-sidebar {
    position: fixed;
    height: 100vh;
  }
}

.wrapper.hide_logo {
  @media (max-width: 767px) {
    .main-header .logo {
      display: none;
    }
  }
}

.logo-mini,
.logo-lg {
  text-align: center;

  img {
    padding: .4em !important;
  }
}

.logo-lg {
  img {
    display: -webkit-inline-box;
    width: 25%;
  }
}

.user-panel {
  height: 4em;
}

hr.visible-xs-block {
  width: 100%;
  background-color: rgba(0, 0, 0, 0.17);
  height: 1px;
  border-color: transparent;
}

.navbar .search-form {
  width: 200px;
  height: 30px;
}
@media (max-width: 767px) {
  .navbar .search-form {
    width: 130px;
  }
}
.navbar .search-form input[type="text"] {
  height: 28px;
}
.navbar .search-form .btn {
  height: 30px;
  padding: 0 12px;
}

</style>
