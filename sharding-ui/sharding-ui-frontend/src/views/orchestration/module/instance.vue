<!--
  - Licensed to the Apache Software Foundation (ASF) under one or more
  - contributor license agreements.  See the NOTICE file distributed with
  - this work for additional information regarding copyright ownership.
  - The ASF licenses this file to You under the Apache License, Version 2.0
  - (the "License"); you may not use this file except in compliance with
  - the License.  You may obtain a copy of the License at
  -
  -     http://www.apache.org/licenses/LICENSE-2.0
  -
  - Unless required by applicable law or agreed to in writing, software
  - distributed under the License is distributed on an "AS IS" BASIS,
  - WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  - See the License for the specific language governing permissions and
  - limitations under the License.
  -->

<template>
  <div class="table-wrap">
    <el-table :data="tableData" border style="width: 100%">
      <el-table-column
        v-for="(item, index) in column"
        :key="index"
        :prop="item.prop"
        :label="item.label"
        :width="item.width"
      />
      <el-table-column :label="$t('index.table.operate')" fixed="right" width="80" align="center">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.enabled" @change="handleChange(scope.row)"/>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
        :total="total"
        :current-page="currentPage"
        background
        layout="prev, pager, next"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script>
import _ from 'lodash'
import API from '../api'
export default {
  name: 'Instance',
  data() {
    return {
      column: [
        {
          label: this.$t('orchestration').instance.serverIp,
          prop: 'serverIp'
        },
        {
          label: this.$t('orchestration').instance.instanceId,
          prop: 'instanceId'
        }
      ],
      tableData: [],
      cloneTableData: [],
      currentPage: 1,
      pageSize: 10,
      total: null
    }
  },
  created() {
    this.getOrcheInstance()
  },
  methods: {
    handleCurrentChange(val) {
      const data = _.clone(this.cloneTableData)
      this.tableData = data.splice(val - 1, this.pageSize)
    },
    getOrcheInstance() {
      API.getOrcheInstance().then(res => {
        const data = res.model
        this.total = data.length
        this.cloneTableData = _.clone(res.model)
        this.tableData = data.splice(0, this.pageSize)
      })
    },
    putOrcheInstance(row) {
      API.putOrcheInstance(row).then(res => {
        this.getOrcheInstance()
      })
    },
    handleChange(row) {
      this.putOrcheInstance(row)
    }
  }
}
</script>

<style lang="scss" scoped>
.table-wrap {
  margin-top: 20px;
}
.pagination {
  float: right;
  margin: 10px -10px 10px 0;
}
</style>
