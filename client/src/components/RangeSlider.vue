<template>
  <div
    class="py-[0.2rem] flex items-center shadow-xl rounded-full bg-gradient-to-r from-blue-500 to-blue-200 relative"
    @click.self="handleClick"
  >
    <button
      ref="btn1"
      class="p-[0.4rem] rounded-full bg-blue-800 absolute hover:bg-blue-900 border hover:border-gray-400 shadow transition-colors text-white text-sm font-bold"
      @mousedown="(clicked1 = true) && (layerX = $event.layerX)"
      @mousemove.prevent="handleMouseMove"
      @mouseup="clicked1 = false"
      @touchstart="
        (clicked1 = true) && (layerX = $event.target.offsetWidth / 2)
      "
      @touchend="clicked1 = false"
      @touchmove="handleMouseMove"
    >
      {{ low }}
    </button>
    <button
      ref="btn2"
      class="p-[0.4rem] rounded-full bg-blue-800 absolute hover:bg-blue-900 border hover:border-gray-400 shadow transition-colors text-white text-sm font-bold"
      @mousedown="(clicked2 = true) && (layerX = $event.layerX)"
      @mousemove.prevent="handleMouseMove"
      @mouseup="clicked2 = false"
      @touchstart="
        (clicked2 = true) && (layerX = $event.target.offsetWidth / 2)
      "
      @touchend="clicked2 = false"
      @touchmove="handleMouseMove"
    >
      {{ high }}
    </button>
  </div>
</template>

<script>
import { ref } from "vue";

export default {
  props: {
    values: {
      type: Object,
      default: () => ({ high: -1, low: -1 }),
      required: true,
    },
    min: {
      type: Number,
    },
    max: {
      type: Number,
    },
  },
  emits: ["anyChangesHandle"],
  data() {
    return {
      clicked1: false,
      clicked2: false,
      low: this.min,
      high: this.max,
      absOffset: { left: 0, top: 0 },
      layerX: 0,
    };
  },
  setup() {
    const btn1 = ref(null);
    const btn2 = ref(null);
    return { btn1, btn2 };
  },
  methods: {
    handleClick(event) {
      if (event.clientX - this.absOffset.left < this.btn1.offsetLeft) {
        this.btn1.style.left =
          event.clientX -
          this.absOffset.left -
          this.btn1.offsetWidth / 2 +
          "px";
        this.$emit("anyChangesHandle");
      } else if (
        event.clientX - this.absOffset.left >
        this.btn2.offsetLeft + this.btn2.offsetWidth
      ) {
        this.btn2.style.left =
          event.clientX -
          this.absOffset.left -
          this.btn2.offsetWidth / 2 +
          "px";
        this.$emit("anyChangesHandle");
      }
      this.low = calculateValue(this.btn1, this.min, this.max);
      this.high = calculateValue(this.btn2, this.min, this.max);
    },
    handleMouseMove() {
      const slideBtn = (btn, limit, dir, clientX) => {
        let res = clientX - this.absOffset.left - this.layerX;
        if (
          res >= -btn.offsetWidth / 2 &&
          res <= btn.parentNode.offsetWidth - btn.offsetWidth / 2 &&
          ((dir == "left" && res < limit) || (dir == "right" && res > limit))
        ) {
          this.$emit("anyChangesHandle");
          btn.style.left = res + "px";
        }
      };
      const moveEvent = (clientX) => {
        if (this.clicked1) {
          if (this.btn1.offsetLeft < this.btn2.offsetLeft) {
            slideBtn(this.btn1, this.btn2.offsetLeft, "left", clientX);
            this.low = calculateValue(this.btn1, this.min, this.max);
          } else this.btn1.style.left = this.btn2.offsetLeft - 1 + "px";
        }
        if (this.clicked2) {
          if (this.btn2.offsetLeft > this.btn1.offsetLeft) {
            slideBtn(this.btn2, this.btn1.offsetLeft, "right", clientX);
            this.high = calculateValue(this.btn2, this.min, this.max);
          } else this.btn2.style.left = this.btn1.offsetLeft + 1 + "px";
        }
      };

      window.addEventListener("mousemove", (event) => moveEvent(event.clientX));
      window.addEventListener("touchmove", (event) =>
        moveEvent(event.touches[0].clientX)
      );

      const upEvent = () => {
        this.layerX = 0;
        this.clicked1 = this.clicked2 = false;
        this.$emit("update:values", { low: this.low, high: this.high });
      };

      window.addEventListener("mouseup", upEvent);
      window.addEventListener("touchend", upEvent);
    },
    resizeHandle() {
      this.absOffset = cumulativeOffset(this.btn1.parentNode);
      this.btn1.style.left =
        (this.btn1.parentNode.offsetWidth * (this.low - this.min)) /
          (this.max - this.min) -
        this.btn1.offsetWidth / 2 +
        "px";
      this.btn2.style.left =
        (this.btn2.parentNode.offsetWidth * (this.high - this.min)) /
          (this.max - this.min) -
        this.btn2.offsetWidth / 2 +
        "px";
    },
  },
  mounted() {
    window.addEventListener("resize", () => this.resizeHandle());
    this.resizeHandle();
  },
  watch: {
    min() {
      this.low = calculateValue(this.btn1, this.min, this.max);
      this.high = calculateValue(this.btn2, this.min, this.max);
      this.resizeHandle();
    },
    max() {
      this.low = calculateValue(this.btn1, this.min, this.max);
      this.high = calculateValue(this.btn2, this.min, this.max);
      this.resizeHandle();
    },
  },
};

function calculateValue(btn, min, max) {
  return parseInt(
    (max - min) *
      ((btn.offsetLeft + btn.offsetWidth / 2) / btn.parentNode.offsetWidth) +
      min
  );
}

var cumulativeOffset = function (element) {
  let top = 0,
    left = 0;
  do {
    top += element.offsetTop || 0;
    left += element.offsetLeft || 0;
    element = element.offsetParent;
  } while (element);

  return {
    top: top,
    left: left,
  };
};
</script>
