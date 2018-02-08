<template>
  <div>
    <div v-for="flag in publicFlags" :key="flag.id">
      <label>
        <input type="radio" name="type" :value="flag.id" v-model="article.publicFlag">
        <i :class="'fa ' + flag.icon" aria-hidden="true"></i>
        {{ $t(flag.name) }}
        {{article.publicFlag}}
      </label>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import logger from 'logger'
const LABEL = 'ArticlePartsTarget.vue'

export default {
  name: 'ArticlePartsTarget',
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
    changeArticle: function () {
      logger.info(LABEL, 'article is changed.')
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
