<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->
<script>
  /**
   * @example copyfields({ a: 1, b: 2, c: 3 }, ['a', 'c']) == { a: 1, c: 3 }
   */
  function copyFields(obj, fields) {
    const newObj = {}
    for (const field of fields) newObj[field] = obj[field]
    return newObj
  }

  function fieldsEquals(obj1, obj2, fields) {
    return fields.every(f => obj1[f] === obj2[f])
  }
</script>
