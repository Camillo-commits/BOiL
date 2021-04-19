// eagerly import theme styles so as we can override them
import '@vaadin/vaadin-lumo-styles/all-imports';

const $_documentContainer = document.createElement('template');

$_documentContainer.innerHTML = `
<custom-style>
<style include='lumo-badge'>
        html {
      --lumo-primary-color: hsl(214, 0%, 0%);
      --lumo-primary-text-color: hsl(214, 0%, 0%);
      --lumo-error-text-color: hsl(3, 100%, 59%);
      --lumo-base-color: #fafafa;
      --lumo-body-text-color: hsla(214, 0%, 0%, 0.94);

    }

</style>
</custom-style>


`;

document.head.appendChild($_documentContainer.content);
