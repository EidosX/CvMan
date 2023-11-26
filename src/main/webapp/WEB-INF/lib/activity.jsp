<!--
 Copyright (c) 2023 Diego Imbert
 
 This software is released under the MIT License.
 https://opensource.org/licenses/MIT
-->
<script>
  function formatActivityType(type) {
    switch (type) {
      case "TRAINING":
        return "Formation"
      case "OTHER":
        return "Autre"
      case "PROFESSIONAL_EXPERIENCE":
        return "Experience professionnelle"
      case "PROJECT":
        return "Projet"
    }
  }
  const allActivityTypes = ["TRAINING", "OTHER", "PROFESSIONAL_EXPERIENCE", "PROJECT"]
</script>
