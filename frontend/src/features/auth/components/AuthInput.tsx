type InputPropsType = {
  id: string;
  type: string;
  placeholder?: string;
  value?: string;
  onChange?: (e: React.ChangeEvent<HTMLInputElement, HTMLInputElement>) => void;
  onClick?: (e: React.MouseEvent<HTMLInputElement>) => void;
  className?: string;
  required?: boolean;
  disabled?: boolean;
};

const AuthInput = ({
  placeholder,
  type,
  value,
  onChange,
  onClick,
  className,
  required,
  id,
  disabled,
}: InputPropsType) => {
  return (
    <input
      id={id}
      disabled={disabled}
      required={required}
      onChange={onChange}
      onClick={onClick}
      type={type}
      placeholder={placeholder}
      value={value}
      className={` w-full rounded-xl border border-slate-200 bg-white px-4 py-3
        text-sm text-slate-900 placeholder:text-slate-400
        outline-none transition-all duration-200
        hover:border-slate-300
        focus:border-blue-500 focus:ring-4 focus:ring-blue-500/10
        disabled:cursor-not-allowed disabled:bg-slate-50

        ${className}
        `}
    />
  );
};

export default AuthInput;
