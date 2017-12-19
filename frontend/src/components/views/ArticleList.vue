<template>
  <!-- Main content -->
  <div class="content">
    <h1>{{ $t('Route.ArticleListTitle') }}</h1>

    <div class="row">
      <div class="col-md-12">
      {{ $t('ArticleList.Message') }}
      </div>
    </div>

    <a href="#" v-on:click.prevent="handleClick_changeLanguage('en')">英語</a>
    <a href="#" v-on:click.prevent="handleClick_changeLanguage('ja')">日本語</a>

    <hr/>
    <a href="#" v-on:click.prevent="testAddMsgCount">通知</a>

  </div>

</template>

<script>
  export default {
    name: 'ArticleList',
    methods: {
      handleClick_changeLanguage (lang) {
        this.$i18n.locale = lang
      },
      testAddMsgCount () {
        console.log(this.$store.state)
        this.$store.commit('SET_USER_INFO', {
          messages: [],
          notifications: [{1: 'test', 2: 'test'}],
          tasks: []
        })
      }
    }
  }
</script>
