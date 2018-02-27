<template>
  <div>

    <div class="box box-info">
      <div class="box-header with-border">
        <h3 class="box-title">Viewers</h3>
      </div>
      <div class="box-body text-black" v-for="flag in publicFlags" :key="flag.id">
        <label>
          <input type="radio" name="publicFlag" :value="flag.id" v-model="article.publicFlag">
          <i :class="'fa ' + flag.icon" aria-hidden="true"></i>
          {{ $t(flag.name) }}
        </label>
      </div>
    </div>

    <transition
      name="custom-classes-transition"
      enter-active-class="animated fadeIn"
      leave-active-class="animated fadeOut"
      v-on:after-enter="triggerResizeWindow"
      v-on:after-leave="triggerResizeWindow"
    >
      <div class="box box-success" v-show="article.publicFlag === 2">
        <div class="box-header with-border">
          <h3 class="box-title">Targets</h3>
          <div class="box-tools pull-right">
            <button type="button" class="btn btn-box-tool" v-on:click="selectTargets()">
              <i class="fa fa-plus-circle"></i>
            </button>
          </div>
        </div>

        <div if="article.viewers">
          <div v-for="vgroup in article.viewers.groups" :key="vgroup.id" class="box-body text-black">
            <i class="fa fa-users text-light-blue" aria-hidden="true"></i>&nbsp;{{vgroup.name | abbreviate(20)}}
            <div class="box-tools pull-right">
              <button type="button" class="btn btn-box-tool" v-on:click="removeViewer(vgroup)"><i class="fa fa-minus-circle"></i></button>
            </div>
          </div>
        </div>

        <div if="article.viewers">
          <div v-for="vuser in article.viewers.users" :key="vuser.id" class="box-body text-black">
            <i class="fa fa-user text-olive" aria-hidden="true"></i>&nbsp;{{vuser.name | abbreviate(20)}}
            <div class="box-tools pull-right">
              <button type="button" class="btn btn-box-tool" v-on:click="removeViewer(vuser)"><i class="fa fa-minus-circle"></i></button>
            </div>
          </div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import 'animate.css/animate.min.css'

import { mapState } from 'vuex'
import logger from 'logger'
const LABEL = 'ArticlePartsViewersSelect.vue'

export default {
  name: 'ArticlePartsViewersSelect',
  computed: {
    ...mapState({
      article: state => state.article.article
    })
  },
  data () {
    return {
      publicFlags: [
        {id: 0, name: 'Article.PublicFlag.0', icon: 'fa-globe'},
        {id: 1, name: 'Article.PublicFlag.1', icon: 'fa-lock'},
        {id: 2, name: 'Article.PublicFlag.2', icon: 'fa-address-book-o'}
      ]
    }
  },
  watch: {
    'article': 'changeArticle'
  },
  methods: {
    triggerResizeWindow: function () {
      return this.$store.dispatch('pagestate/triggerResize')
    },
    changeArticle: function () {
      logger.debug(LABEL, 'article is changed.')
    },
    selectTargets: function () {
      this.$store.dispatch('targets/showDialog', {
        selected: this.article.viewers
      })
    },
    removeViewer: function (target) {
      this.$store.dispatch('article/removeViewer', target)
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
