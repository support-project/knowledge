<template>
  <ul class="sidebar-menu">

    <router-link tag="li" class="pageLink" to="/login" v-if="!user.userId">
      <a>
        <i class="fa fa-sign-in text-purple"></i>
        <span class="page"> Login {{ user.userId }}</span>
      </a>
    </router-link>
    <li class="pageLink router-link-active" v-if="user.userId">
      <a href="#" v-on:click.prevent="logout">
        <i class="fa fa-sign-out text-yellow"></i>
        <span class="page"> Logout</span>
      </a>
    </li>

    <li class="header">Main</li>
    
    <router-link tag="li" class="pageLink" to="/articles/new">
      <a>
        <i class="fa fa-plus-circle text-blue"></i>
        <span class="page">{{ $t('Route.ArticleCreate') }}</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/drafts">
      <a>
        <i class="fa fa-pencil-square-o text-olive"></i>
        <span class="page">{{ $t('Route.DraftList') }}</span>
      </a>
    </router-link>

    <router-link tag="li" class="pageLink" to="/">
      <a>
        <i class="fa fa-list text-olive"></i>
        <span class="page">{{ $t('Route.ArticleList') }}</span>
      </a>
    </router-link>

    <router-link tag="li" class="pageLink" to="/articles/search">
      <a>
        <i class="fa fa-search text-olive"></i>
        <span class="page">{{ $t('Route.ArticleSearch') }}</span>
      </a>
    </router-link>

    <router-link tag="li" class="pageLink" to="/myarticles">
      <a>
        <i class="fa fa-user-circle text-olive"></i>
        <span class="page">{{ $t('Route.MyArticleList') }}</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/stocks">
      <a>
        <i class="fa fa-bookmark-o text-olive"></i>
        <span class="page">{{ $t('Route.StockList') }}</span>
      </a>
    </router-link>

    <li class="header">Custom</li>
    <li class="pageLink">
      <a href="open.knowledge/list">
        <i class="fa fa-recycle" aria-hidden="true"></i>
        <span class="page">{{ $t('Route.MoveOldUI') }}</span>
      </a>
    </li>
    <router-link tag="li" class="pageLink" to="/config">
      <a>
        <i class="fa fa-cog"></i>
        <span class="page">{{ $t('Route.Config') }}</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/localize">
      <a>
        <i class="fa fa-language"></i>
        <span class="page">{{ $t('Route.ConfigLocalize') }}</span>
      </a>
    </router-link>


    <!--
    <router-link tag="li" class="pageLink" to="/events">
      <a>
        <i class="fa fa-list"></i>
        <span class="page">イベント</span>
      </a>
    </router-link>

    <router-link tag="li" class="pageLink" to="/groups">
      <a>
        <i class="fa fa-list"></i>
        <span class="page">グループ</span>
      </a>
    </router-link>

    <router-link tag="li" class="pageLink" to="/tags">
      <a>
        <i class="fa fa-list"></i>
        <span class="page">タグ</span>
      </a>
    </router-link>

    <li class="header">Test</li>
    <router-link tag="li" class="pageLink" to="/test">
      <a>
        <i class="fa fa-cogs"></i>
        <span class="page">{{ $t('Route.TestViewTitle') }}</span>
      </a>
    </router-link>


    <li class="header">TOOLS</li>
    <router-link tag="li" class="pageLink" to="/dashboard">
      <a>
        <i class="fa fa-desktop"></i>
        <span class="page">Dashboard</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/tables">
      <a>
        <i class="fa fa-table"></i>
        <span class="page">Tables</span>
      </a>
    </router-link>

    <li class="header">Tools</li>

    <router-link tag="li" class="pageLink" to="/tasks">
      <a>
        <i class="fa fa-tasks"></i>
        <span class="page">Tasks</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/setting">
      <a>
        <i class="fa fa-cog"></i>
        <span class="page">Settings</span>
      </a>
    </router-link>
    <li class="treeview">
      <a href="#">
        <i class="fa fa-folder-o"></i>
        <span>Files</span>
        <span class="pull-right-container">
          <i class="fa fa-angle-left fa-fw pull-right"></i>
        </span>
      </a>
      <ul class="treeview-menu">
        <li>
          <a href="#">
            <i class="fa fa-file-word-o"></i> Item 1
          </a>
        </li>
        <li>
          <a href="#">
            <i class="fa fa-file-picture-o"></i> Item 2
          </a>
        </li>
        <li>
          <a href="#">
            <i class="fa fa-file-pdf-o"></i> Item 3
          </a>
        </li>
      </ul>
    </li>

    <li class="header">LOGS</li>
    <router-link tag="li" class="pageLink" to="/access">
      <a>
        <i class="fa fa-book"></i>
        <span class="page">Access</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/server">
      <a>
        <i class="fa fa-hdd-o"></i>
        <span class="page">Server</span>
      </a>
    </router-link>
    <router-link tag="li" class="pageLink" to="/repos">
      <a>
        <i class="fa fa-heart"></i>
        <span class="page">Repos</span>
        <small class="label pull-right bg-green">AJAX</small>
      </a>
    </router-link>

    <li class="header">PAGES</li>
    <router-link tag="li" class="pageLink" to="/404">
      <a>
        <i class="fa fa-circle-o text-red"></i>
        <span class="page"> 404</span>
      </a>
    </router-link>
    -->
  </ul>

</template>
<script>
export default {
  name: 'SidebarName',
  props: ['user'],
  methods: {
    logout () {
      this.$store.dispatch('auth/logout').then(() => {
        return this.$store.dispatch('user/loadUserInformation', {
          i18n: this.$i18n
        })
      }).then(() => {
        this.$router.push('/login')
      })
    }
  }
}
</script>
<style>
  /* override default */
  .sidebar-menu>li>a {
    padding: 12px 15px 12px 15px;
  }

  .sidebar-menu li.active>a>.fa-angle-left, .sidebar-menu li.active>a>.pull-right-container>.fa-angle-left {
    animation-name: rotate;
    animation-duration: .2s;
    animation-fill-mode: forwards;
  }

  @keyframes rotate {
    0% {
      transform: rotate(0deg);
    }

    100% {
      transform: rotate(-90deg);
    }
  }
</style>
