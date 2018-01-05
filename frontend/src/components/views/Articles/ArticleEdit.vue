<template>
  <div>
    <page-title
      :title = "'Route.' + $route.name"
      :description = "$route.name + '.description'"
      :breadcrumb = "breadcrumb" />

    <div id="secondNavbar">
      <nav class="secondMenu" >
        <button :title="$t('ArticleEdit.BtnRelease')" class="label-primary">
          <i class="fa fa-rocket fa-lg" aria-hidden="true"></i>
        </button>
        <a :title="$t('ArticleEdit.BtnDraft')">
          <i class="fa fa-save fa-lg" aria-hidden="true"></i>
        </a>
        <a id="toggleAttributes" :title="$t('ArticleEdit.BtnAttributes')"
          v-bind:class="{'toggle-on': pagestate.toggleAttributes}">
          <i class="fa fa-list fa-lg" aria-hidden="true"></i>
        </a>
        <router-link tag="a" :to="'/articles/' + $route.params.id" class="bg-gray"
          :title="$t('ArticleEdit.BtnCancel')">
          <i class="fa fa-undo fa-lg" aria-hidden="true"></i>
        </router-link>
        <a :title="$t('ArticleEdit.BtnRemove')" class="label-danger">
          <i class="fa fa-remove fa-lg" aria-hidden="true"></i>
        </a>
      </nav>
    </div>

    <!-- Main content -->
    <div class="content">
      #{{ $route.params.id }}
      Edit
    </div>
  </div>
</template>

<script>
import tippy from 'tippy.js'
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

import secondNavbar from '../../../lib/displayParts/secondNavbar'

export default {
  name: 'ArticleDetail',
  data () {
    return {
      breadcrumb: [
//        {to: '/articles', name: 'Route.ArticleList'},
        {to: '/articles/' + this.$route.params.id, name: 'Route.ArticleDetail'},
        {to: '/articles/' + this.$route.params.id + '/edit', name: 'Route.ArticleEdit'}
      ]
    }
  },
  components: { PageTitle },
  computed: {
    ...mapState([
      'pagestate',
      'resources'
    ])
  },
  mounted () {
    // 画面表示時に読み込み
    this.$nextTick(() => {
      // この画面特有の操作ボタンにTips表示
      tippy('[title]', {
        placement: 'bottom',
        animation: 'scale',
        duration: 200,
        arrow: true
      })
      // この画面特有の操作ボタンの固定
      secondNavbar()
    })
  }
}
</script>
