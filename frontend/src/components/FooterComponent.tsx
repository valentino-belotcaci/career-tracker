import { useTranslation } from 'react-i18next';


export default function FooterComponent() {

  const { t } = useTranslation();

  return (
    <footer className="app-footer">
      {t('footerContent')}
    </footer>
  );
}
