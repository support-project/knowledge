<template>
  <span>
    <!--
    <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/people" data-target="#emojiPeopleModal">people</a>
    <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/nature" data-target="#emojiNatureModal">nature</a>
    <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/objects" data-target="#emojiObjectsModal">objects</a>
    <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/places" data-target="#emojiPlacesModal">places</a>
    <a data-toggle="modal" href="<%= request.getContextPath()%>/open.emoji/symbols" data-target="#emojiSymbolsModal">symbols</a>
    -->
    
    <a v-on:click="emojiSelect('hello!')">emoji</a>

  </span>
</template>

<script>
export default {
  name: 'ArticlePartsEmoji',
  props: ['contents'],
  methods: {
    emojiSelect: function (val) {
      this.$emit('emoji-select', val)
    }
  }
}
</script>

<style>

</style>
