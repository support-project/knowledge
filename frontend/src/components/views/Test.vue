<template>
  <!-- Main content -->
  <div class="content">
    <h1>{{ $t('Route.TestViewTitle') }}</h1>

    <div class="row">
      <div class="col-md-12">
      {{ $t('TestView.Message') }}
      </div>
    </div>

    <a href="#" v-on:click.prevent="handleClick_changeLanguage('en')">英語</a>
    <a href="#" v-on:click.prevent="handleClick_changeLanguage('ja')">日本語</a>

    <hr/>
    <a href="#" v-on:click.prevent="testAddMsgCount">通知</a>

    <hr/>
    

    <hr/>
    <a href="#" v-on:click.prevent="changeMsg">メッセージ変更</a><br/>
    <a href="#" v-on:click.prevent="apisample1">API Sample1(OPEN)</a><br/>
    <a href="#" v-on:click.prevent="apisample2">API Sample2(CLOSE)</a><br/>
    <br/>

    {{ msg }}

    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/>
    <br/> a


  </div>

</template>

<script>
  import api from '../../api'

  export default {
    name: 'TestView',
    data () {
      return {
        msg: 'sample!'
      }
    },
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
      },
      changeMsg () {
        this.msg = '変更してみた'
      },
      apisample1 () {
        api.request('get', '/api/sample3')
        .then(response => {
          console.log(response.data)
          this.msg = response.data.msg
        })
        .catch(error => {
          console.log(error)
        })
      },
      apisample2 () {
        console.log(this.$store.getters.GET_TOKEN)

        api.request('get', '/api/sample', null, this.$store.getters.GET_TOKEN)
        .then(response => {
          console.log(response.data)
          this.msg = response.data.msg
        })
        .catch(error => {
          console.log(error)
          this.msg = 'access error. ' + error.response.data.msg
        })
      }
    }
  }
</script>
