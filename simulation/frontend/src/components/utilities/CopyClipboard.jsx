import React from "react"
import { ClipboardIcon, ClipboardCheckIcon } from "@heroicons/react/solid"

export default function CopyClipboard(props) {
  const json = props.json || []
  const [copied, setCopied] = React.useState(false)
  const handleCopied = () => {
    setCopied(!copied)
    navigator.clipboard.writeText(JSON.stringify(json))
  }

  React.useEffect(() => {
    if (copied)
      setTimeout(() => {
        setCopied(!copied)
      }, 5000)
  }, [copied, setCopied])

  return (
    <button
      onClick={handleCopied}
      className={`
        absolute right-${json.length === 0 ? 2 : 6} top-2 z-50 text-white p-1 rounded-full
        bg-gradient-to-br hover:opacity-80 duration-200
        ${copied ? `from-rose-300 via-rose-500 to-rose-800` : `from-teal-300 via-blue-300 to-violet-300`}`}
    >
      <span className="flex text-sm text-white">
        &nbsp;{copied ? "Copied!" : "Copy"}
        {copied ? <ClipboardCheckIcon className="ml-0.5 mt-0.5 w-4 h-4" /> : <ClipboardIcon className="ml-0.5 mt-0.5 w-4 h-4" />}
      </span>
    </button>
  )
}