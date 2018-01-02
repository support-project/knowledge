<template>
  <span v-if="article.insertUser">
    <a :href="'open.account/info/' + article.insertUser" class="text-primary btn-link">
      <img v-lazy="article.insertUserIcon" alt="icon" width="18" height="18" />
      {{article.insertUserName}}
    </a>
    -
    <i class="fa fa-calendar-plus-o" aria-hidden="true"></i>&nbsp;{{article.insertDatetime | moment}}
    <span v-if="article.insertDatetime !== article.updateDatetime" class="update_info">
      <i class="fa fa-angle-double-right" aria-hidden="true"></i>
      <a :href="'open.account/info/' + article.updateUser" class="text-primary btn-link">
        <img v-lazy="article.updateUserIcon" alt="icon" width="18" height="18" />
        {{article.updateUserName}}
      </a>
      -
      <i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp;{{article.updateDatetime | moment}}
    </span>
  </span>
</template>

<script>
import moment from 'moment'
export default {
  name: 'ArticlePartsEditor',
  props: ['article'],
  filters: {
    moment: function (date) {
      return moment(date).format('YYYY/MM/DD HH:mm')
    }
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
