<template>

  <div>
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
      <page-title
        :title = "'Route.' + $route.name"
        :description = "$route.name + '.description'"
        :breadcrumb = "breadcrumb" />

      <!-- Main content -->
      <div class="content main-content">

        {{$t('ConfigLocalize.Language')}}: 
        <span v-if="!edit">
          <span v-if="$i18n.locale == 'ja'">
            {{$t('ConfigLocalize.Japanese')}}
          </span>
          <span v-if="$i18n.locale != 'ja'">
            {{$t('ConfigLocalize.English')}}
          </span>
          <button class="btn btn-default" v-on:click.prevent="handleClick_edit">
            {{$t('ConfigLocalize.Edit')}}
          </button>
        </span>

        <span v-if="edit">
          <button class="btn btn-default" v-on:click.prevent="handleClick_changeLanguage('en')">
            {{$t('ConfigLocalize.English')}}
          </button>
          <button class="btn btn-default" v-on:click.prevent="handleClick_changeLanguage('ja')">
            {{$t('ConfigLocalize.Japanese')}}
          </button>
        </span>

      </div>
    </div>
    <!-- /.content-wrapper -->
  </div>
</template>

<script>
import { mapState } from 'vuex'
import PageTitle from '../Parts/PageTitle'

export default {
  name: 'ConfigLocalize',
  components: { PageTitle },
  data () {
    return {
      breadcrumb: [
        {to: '/lang', name: 'Route.ConfigLocalize'}
      ],
      edit: false
    }
  },
  computed: {
    ...mapState({
      item: state => state.stocks.item,
      pagination: state => state.stocks.articlePagination
    })
  },
  methods: {
    handleClick_changeLanguage (lang) {
      this.$store.dispatch('user/setLanguage', {
        $i18n: this.$i18n,
        lang: lang
      }).then(() => {
        this.edit = false
      })
    },
    handleClick_edit () {
      this.edit = true
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>

<style>

</style>
