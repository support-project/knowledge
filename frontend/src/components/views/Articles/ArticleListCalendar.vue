<template>
  <div>
    <h5>- <i class="fa fa-calendar"></i>&nbsp;イベント - </h5>
    <div class="events">
        <span id="loading" class="hide"><i class="fa fa-spinner fa-pulse fa-1x fa-fw"></i></span>
     <datepicker :inline="true" class="calendar"></datepicker>
   </div>
  </div>
</template>

<script>
import Datepicker from 'vuejs-datepicker'

export default {
  name: 'ArticleListCalendar',
  components: { Datepicker }
}
</script>

<style>
.events {
  width: 100%;
  text-align: center;
  border: 1px solid #ecf0f1;
  border-radius: 4px;
}

.datepicker,
.table-condensed {
  width: 100%;
  min-width: 280px;
}

.datepicker table tr td.eventday {
  color: #000;
  background-color: #D0F5A9;
  border-color: #D0F5A9;
}
</style>
