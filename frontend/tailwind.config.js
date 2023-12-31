const defaultTheme = require("tailwindcss/defaultTheme")

module.exports = {
  darkMode: "class",
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      fontSize: {
        xxs: "0.6rem",
      },
      minWidth: {
        0: "0",
        "1/5": "20%",
        "1/4": "25%",
        "2/5": "40%",
        "1/2": "50%",
        "3/5": "60%",
        "3/4": "75%",
        "4/5": "80%",
        full: "100%",
        screen: "100vh",
      },
      maxWidth: {
        0: "0",
        "1/5": "20%",
        "1/4": "25%",
        "2/5": "40%",
        "1/2": "50%",
        "3/5": "60%",
        "3/4": "75%",
        "4/5": "80%",
        full: "100%",
      },
      fontFamily: {
        sans: ["Inter var", ...defaultTheme.fontFamily.sans],
        mono: ["Fira Code", ...defaultTheme.fontFamily.mono],
        source: ["Source Sans Pro", ...defaultTheme.fontFamily.sans],
        "ubuntu-mono": ["Ubuntu Mono", ...defaultTheme.fontFamily.mono],
        system: defaultTheme.fontFamily.sans,
        flow: "Flow",
      },
      backgroundImage: { "hero": "url('/src/components/img/hero.jpg')" },
    },
  },
  plugins: [require("@tailwindcss/forms")],
}
