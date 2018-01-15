<template>
  <div v-if="resources.article.type">
    <div class="form-group" v-for="item in resources.article.type.items" :key="item.id">
      <label>{{item.itemName}} - {{item.itemType}}</label>
      <span v-if="item.itemType === 0">
        <input type="text" class="form-control" v-model="item.itemValue" :name="'item_' + item.itemNo" />
      </span>
      <span v-if="item.itemType === 1">
        <textarea class="form-control"  v-model="item.itemValue" :name="'item_' + item.itemNo"></textarea>
      </span>
      <span v-if="item.itemType === 2">
        <input type="number" class="form-control" v-model="item.itemValue" min="0" step="1" :name="'item_' + item.itemNo" />
      </span>
      <span v-if="item.itemType === 10">
        <br/>
        <label class="radio-inline" v-for="choice in item.choices" :key="choice.choiceNo">
          <input type="radio" class="" :value="choice.choiceValue" v-model="item.itemValue" :name="'item_' + item.itemNo" />
          &nbsp;{{choice.choiceLabel}}
        </label>
      </span>
      <span v-if="item.itemType === 11">
        <br/>
        <label class="checkbox-inline" v-for="choice in item.choices" :key="choice.choiceNo">
          <input type="checkbox" class="" :value="choice.choiceValue" v-model="item.itemValue" :name="'item_' + item.itemNo" />
          &nbsp;{{choice.choiceLabel}}
        </label>
      </span>
  

    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'ArticleEditItems',
  computed: {
    ...mapState([
      'resources'
    ])
  },
  mounted () {
    this.$nextTick(() => {
    })
  }
}
</script>
